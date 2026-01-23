# GenMcScript  Settings Module

This document describes the **Settings** module for **GenMcScript**. It is a focused, professional guide explaining how to configure and manage persistent defaults used by the generator CLI. The `settings` subcommand is intended **only** for configuration (it does not generate assets).

---

## Overview

The Settings module stores reusable defaults such as `namespace`, `path`, and `autoplace`. These defaults are read automatically when you run the `gen` command. Any value provided directly to `gen` overrides the saved setting for that run.

Using settings reduces repetition and keeps generation commands concise and predictable across a project.

---

## CLI Alias (Recommended)

For convenience you can create a shell alias to run the JAR quickly. On Linux or macOS:

```bash
alias gms="java -jar GenMcScript.jar"
```

After creating the alias, the examples below assume the `gms` command.

---

## Command: `gms settings`

**Purpose:** configure persistent defaults for the current project.

**Behavior:** this command updates stored settings only. It does not generate models.

```bash
gms settings [option]
```

### Available Options

| Option                     | Description                                                                                        |
|----------------------------| -------------------------------------------------------------------------------------------------- |
| `-n`, `--namespace`        | Set the default mod namespace (e.g. `examplemod`)                                                  |
| `-p`, `--path`             | Set the project resources path where generated JSON files will be written (see requirements below) |
| `-a`, `-ap`, `--autoplace` | Enable or disable automatic placement of generated files into `assets` / `data` directories        |
| `-r`, `--reset`            | Reset all saved settings to defaults                                                               |

## Autoplace — explanation and requirements

`autoplace` is a convenience feature that places generated files automatically into the appropriate Minecraft resource directories (for example `assets/<namespace>/models/block` or `data/<namespace>/recipes`). When `autoplace` is enabled, GenMcScript will write files directly into these folders so you do not need to move them manually.

**Important requirement:** when `autoplace` is enabled, the `--path` value **must point to your project resources root**, typically:

```
/path/to/project/src/main/resources/
```

That resources root must contain the `assets/` and `data/` folders (at minimum). GenMcScript will place files under those folders according to Minecraft conventions.

If `autoplace` is disabled, files are written to the configured `--path` under a generated subfolder structure and you can move them manually if desired.

---

## Examples (using the `gms` alias)

### Set the namespace

```bash
gms settings -n examplemod
```

### Set the project resources path (recommended path layout)

```bash
gms settings --path /home/yourname/examplemod/src/main/resources
```

This path should point to the `resources` directory that contains `assets/` and `data/`.

### Enable autoplace

```bash
gms settings --autoplace true
```

With `autoplace` enabled, generated files will be written directly into `assets/<namespace>/...` and `data/<namespace>/...` inside the configured `--path`.

### Disable autoplace

```bash
gms settings --autoplace false
```

### Reset settings to defaults

```bash
gms settings --reset
# or
gms settings -r
```

---

## Usage notes

* Settings are **persisted** (for the current user/project according to your implementation). They are read automatically by the `gen` command.
* Any option specified on the `gen` command line (e.g. `gen --namespace othermod`) will **override** the saved settings for that invocation only.
* If a required value is missing from both the `gen` command and saved settings, generation will fail with a clear error message.

---

## Minimal recommended workflow

1. Configure the project once:

```bash
gms settings --namespace examplemod --path /path/to/project/src/main/resources --autoplace true
```

2. Generate assets repeatedly using short commands — settings keep the commands small:

```bash
gms gen --type BLOCK --material oak_planks
```

3. If you need to override for a single run:

```bash
gms gen --type ITEM --material diamond --namespace specialmod
```

---

## Summary

The `gms settings` subcommand is a focused configuration tool designed to make repeated asset generation easy and predictable. It handles default namespace, output path, and automatic placement of generated files. Use it once per project (or when your project layout changes) to remove friction from the generation workflow.
