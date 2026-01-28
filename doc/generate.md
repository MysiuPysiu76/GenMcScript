# GenMcScript — `gen` Command Usage

This document describes **only** the `gen` command of **GenMcScript**.  
It explains how to use the generation CLI to create Minecraft mod asset files such as models, blockstates, recipes, and loot tables.

This file intentionally does **not** document the `settings` command, except where needed to explain value resolution.

---

## CLI Alias (Recommended)

For convenience you can create a shell alias to run the JAR quickly. On Linux or macOS:

```bash
alias gms="java -jar GenMcScript.jar"
```

## Overview

The `gen` command is responsible for generating asset files based on provided options and saved settings.

General rules:
- Values provided via CLI **override** saved settings
- If a value is **not provided**, it is read from settings
- If a required value is missing from both CLI and settings, the command **fails with an error**

Command syntax:

```bash
gms gen [options]
```

---

### Available Options

| Option                          | Meaning                                                                              | Type  |
|---------------------------------|--------------------------------------------------------------------------------------|-------|
| `-t`, `--type`                  | Model type to generate (**required**)                                                | value |
| `-m`, `--material`              | Material used by the model (**required**)                                            | value |
| `-n`, `--namespace`             | Override namespace defined in settings                                               | value |
| `-pt`, `--pattern`              | Pattern or variant used by some model types for example pumpkins or candles          | value |
| `-p`, `--path`                  | Override output directory for generated files                                        | value |
| `-a`, `-ap`, `--autoplace`      | Enable or disable automatic file placement                                           | value |
| `-s`                            | Optional boolean flag used for specific block variants (e.g. brick slab from bricks) | flag  |
| `-sc`, `-st`,  `--stonecutting` | Generate an additional stonecutter recipe                                            | flag  |

#### Option Types

- **value**  
An option that **requires a value** to be provided.  
Example:
```bash
gms gen --type slab --material stone
```


- **flag**
A boolean switch that is enabled just by being present.
If the flag is not provided, it defaults to false.
Example:
```bash
gms gen --type slab --material stone -sc -s
gms gen --type wall --material bricks -s
```
---

## Available Model Types

The `type` parameter defines what kind of Minecraft model and related files will be generated.

| Model Type           | Description                                                  | Minecraft Example      |
|----------------------|--------------------------------------------------------------|------------------------|
| **BLOCK**            | Standard full cube block with the same texture on all sides. | Stone, Dirt            |
| **COLUMN**           | Block with different side and top/bottom textures.           | Oak Log, Quartz Pillar |
| **SLAB**             | Slab variant including bottom, top, and double slab models.  | Stone Slab             |
| **STAIRS**           | Complete stair set (normal, inner, outer).                   | Oak Stairs             |
| **WALL**             | Wall models with all connection variants.                    | Cobblestone Wall       |
| **FENCE**            | Fence with post and side connections.                        | Oak Fence              |
| **BUTTON**           | Button models for wall, floor, and ceiling placement.        | Stone Button           |
| **PRESSURE_PLATE**   | Pressure plate models with pressed/unpressed states.         | Oak Pressure Plate     |
| **ORIENTABLE**       | Block with orientation-based models.                         | Dropper, Dispenser     |
| **BLOCK_BOTTOM_TOP** | Block with separate bottom, top, and side textures.          | TNT                    |
| **PUMPKIN_CARVED**   | Directional block with a carved face.                        | Carved Pumpkin         |
| **PUMPKIN_JACK**     | Pumpkin block with an illuminated face.                      | Jack o’Lantern         |
| **BOOKSHELF**        | Bookshelf-style block with unique side texture.              | Bookshelf              |
| **PLANT**            | Simple cross-model plant.                                    | Grass, Dandelion       |
| **PLANT_TALL**       | Two-block-high plant.                                        | Sunflower, Tall Grass  |
| **PLANT_POT**        | Pot for this plant.                                          | Potted Dendalion       |
| **HEAD**             | Decorative head block.                                       | Player Head            |
| **SKULL**            | Skull-style head with rotation.                              | Skeleton Skull         |
| **CANDLE**           | Candle block with multiple candle states.                    | Candle                 |
| **ITEM**             | Item-only model (no blockstate).                             | Stick, Iron Ingot      |


The `--type` option must match one of the values above.

---

## Required Values

- `type` is always required
- `material` is required for most block-based model types
- If a required value is missing from CLI and settings, generation stops with an error

---

## Multiple Value Generation

Some options support multiple values separated by `/`.

Example:

```bash
gms gen --type block/slab --material oak/stone
```

This generates all combinations:
- BLOCK + oak
- BLOCK + stone
- SLAB + oak
- SLAB + stone

---

## Autoplace Behavior

When `--autoplace` is enabled:
- Generated files are written directly into proper Minecraft directories
- Files are placed under `assets/<namespace>` and `data/<namespace>`

The `--path` value must point to:

```
project/src/main/resources/
```

If autoplace is disabled, files are written into the output directory without automatic project structure placement.

---

## Examples

### Basic generation
```bash
gms gen --type BLOCK --material oak_planks
```

### Override namespace
```bash
gms gen --type item --material diamond --namespace testmod
```

### Generate with pattern
```bash
gms gen --type COLUMN --material stone
```

### Generate multiple variants
```bash
gms gen --type stairs/wall/slab --material granite/diorite
```

### Generate with stonecutter recipe
```bash
gms gen --type SLAB --material stone --stonecutting -s
```

### Enable autoplace for one run
```bash
gms gen --type fence --material spruce --autoplace true
```

---

## Error Cases

The `gen` command exits with an error when:
- `--type` is missing
- `--material` is required but missing
- `--namespace` is when namespace in not entered in settings or gen command
- An invalid model type is provided
- `--autoplace` is enabled and `--path` is invalid

---

## Summary

The `gen` command is the core of GenMcScript.  
It generates Minecraft mod assets based on model type, material, and optional flags, using settings as defaults and CLI arguments as overrides.
