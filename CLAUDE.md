# The Cyborg Age Mod — Developer Guide

## Project Overview

A NeoForge 1.21.1 Minecraft mod adding cybernetic entities and equipment. Players defeat the Ender Dragon, which triggers Cyborg Scout spawns; scouts drop a Cyborg Beacon used to spawn cyborgs, which drop crafting materials. Players craft cybernetic Curio items (cybernetics) that grant abilities powered by a Cyborg Core energy container.

**Mod ID:** `thecyborgage`  
**Java:** 21  
**NeoForge:** 21.1.209  
**Key dependencies:** Curios API 9.5.1+1.21.1, Patchouli 1.21.1-92-NEOFORGE

---

## Build & Verification

**Always compile after changes.** A mod that does not compile is worthless.

```powershell
./gradlew build
```

Run the game in dev:
```powershell
./gradlew runClient
./gradlew runServer
```

Regenerate data (loot tables, recipes, tags, etc.):
```powershell
./gradlew runData
```

---

## Code Standards

### Formatting
Use the **IntelliJ Google Java Format** plugin. All code must conform to it before committing. Key rules:
- 2-space indentation (not 4, not tabs)
- 100-character line limit
- Opening brace on same line (`{` never on its own line)
- `@Override` always present when overriding
- Wildcard imports forbidden — explicit imports only

### Java Conventions
- `final` on fields wherever possible
- `private` by default; widen only when required
- No raw types — always use generics
- Prefer `Optional` over returning `null` from public methods
- Never swallow exceptions silently; log or rethrow
- No magic numbers — use named constants or config values
- Static utility methods go in helper classes (`TCACuriosHelper`, `TCAEntityHelper`), not scattered across items/events

### Safety Rules
- Never call client-only code from server-side paths; guard with `level.isClientSide` or `@Dist.CLIENT`
- Always null-check capability results: `LazyOptional` and `Optional` must be unwrapped safely
- Event handlers must be `static` when annotated `@EventBusSubscriber` at class level
- Do not register the same event handler on both buses accidentally — NeoForge game bus vs. mod bus are distinct
- Config values must be read via `TCAServerConfig` / `TCAClientConfig` accessors, never hardcoded fallbacks

---

## Architecture

### Package Map

```
com.thecyborgage
├── TheCyborgAgeMod.java          # @Mod entry point, wires all registries
├── TCACuriosHelper.java          # Energy R/W on equipped Curio items
├── TCAEntityHelper.java          # Entity physics utilities
├── blocks/                       # Block classes + blockentities/
├── client/                       # Client-only: renderers, models, screens, keybinds
│   └── events/                   # @Dist.CLIENT event handlers
├── config/                       # TCAServerConfig, TCAClientConfig (ModConfigSpec)
├── data/                         # CoreEnergyStorage capability impl
├── enums/                        # RenderLocation
├── events/                       # Server-side event handlers
├── init/                         # DeferredRegister holders (TCAItems, TCABlocks, …)
├── items/                        # All item classes
├── menus/                        # CoreWorkbenchMenu + custom slots
├── mixin/                        # EntityMixin, LivingEntityMixin
├── network/                      # Packets + ServerPayloadHandler
└── entities/                     # CyborgEntity, CyborgScoutEntity
```

### Registry Pattern
All game objects are registered via NeoForge `DeferredRegister` in the `init/` package. Registration happens in `TheCyborgAgeMod` constructor. Follow the existing pattern:

```java
// In TCAItems.java
public static final DeferredItem<MyItem> MY_ITEM =
    ITEMS.registerItem("my_item", MyItem::new, new Item.Properties().stacksTo(1));
```

Never instantiate items/blocks/entities directly — always go through the deferred holder.

### Energy System
- **Cyborg Core** (`CyborgCoreItem`) holds energy via `CORE_ENERGY` data component
- Energy cap is `cyborgCoreMaxEnergy` from server config, scaled by battery upgrades in the Core Workbench
- All cybernetic items consume energy through `TCACuriosHelper.consumeEntityCoreEnergy(entity, amount)`
- Generators (Solar Hat, Thermal Generator, Generator Leg) call `TCACuriosHelper.addEntityCoreEnergy(entity, amount)`
- Energy amounts come from server config values — never hardcode them

### Curios Integration
- Cybernetic items extend `Item` and implement `ICurioItem`
- Tick-based effects go in `curioTick(SlotContext, ItemStack)`
- Capabilities registered in `CapabilityEventHandler` on the NeoForge event bus
- Slot types defined in `src/main/resources/data/thecyborgage/curios/slots/`
- Item-to-slot mapping via tags in `src/main/resources/data/curios/tags/item/`

### Event Handlers
- Server-side handlers: `@EventBusSubscriber(modid = MOD_ID)` — NeoForge game event bus
- Mod-lifecycle handlers: `@EventBusSubscriber(modid = MOD_ID, bus = Bus.MOD)`
- Client-only handlers: add `value = Dist.CLIENT`
- All `@SubscribeEvent` methods on class-level `@EventBusSubscriber` classes **must be static**

### Data Components
Custom data lives in `TCADataComponents`. Use components instead of NBT for all new persistent item data:
```java
public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> MY_COMPONENT =
    DATA_COMPONENTS.registerComponentType("my_component",
        builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));
```

### Configuration
All balance values live in `TCAServerConfig`. Add new values there:
```java
public final ModConfigSpec.IntValue myNewRate;
// in constructor:
this.myNewRate = builder.defineInRange("my_new_rate", 50, 0, Integer.MAX_VALUE);
```
Access via `TheCyborgAgeMod.SERVER_CONFIG.myNewRate.get()`.

---

## Adding a New Cybernetic Item

1. Create `items/MyCyberneticItem.java` extending `Item` implementing `ICurioItem`
2. Register in `TCAItems.java` with `ITEMS.registerItem(...)`
3. Add to creative tab in `TCACreativeModeTabs.java`
4. Register its Curios capability in `CapabilityEventHandler`
5. Add loot / recipe JSON in `src/main/resources/data/thecyborgage/`
6. Add item model JSON in `src/main/resources/assets/thecyborgage/models/item/`
7. Add texture in `src/main/resources/assets/thecyborgage/textures/item/`
8. Add translation key in `src/main/resources/assets/thecyborgage/lang/en_us.json`
9. Add a Patchouli entry in `src/main/resources/data/thecyborgage/patchouli_books/`
10. **Run `./gradlew build` and fix all errors before opening a PR**

---

## Adding a New Entity

1. Create `entities/MyEntity.java` extending an appropriate vanilla base (`Monster`, `PathfinderMob`, etc.)
2. Register in `TCAEntities.java`
3. Add renderer in `client/renderers/`, register in `TheCyborgAgeClient`
4. Define attributes in a static `createAttributes()` method
5. Register attributes in `EntityInitEventHandler`
6. Add loot table JSON in `src/main/resources/data/thecyborgage/loot_table/entities/`
7. Add spawn configuration if world-spawned

---

## Git Workflow

- Branch naming: `neoforge/1.21.x/<type>/<short-description>` (e.g., `neoforge/1.21.x/feat/metabolic-chip`)
- PR target: `main`
- PRs must build cleanly (`./gradlew build` passes) before merge
- Squash trivial fixup commits before opening a PR

---

## Common Pitfalls

| Mistake | Correct approach |
|---|---|
| Hardcoding energy values | Use `TCAServerConfig` fields |
| Client code in server events | Guard with `level.isClientSide` or `@Dist.CLIENT` |
| Non-static `@SubscribeEvent` on class-level subscriber | Make it `static` |
| Registering on wrong event bus | Game events → default bus; mod lifecycle → `Bus.MOD` |
| Skipping `./gradlew build` | Always build before committing |
| Using NBT directly for item data | Use `DataComponentType` via `TCADataComponents` |
| Capability result not checked | Always `ifPresent` / `map` on `LazyOptional` |
