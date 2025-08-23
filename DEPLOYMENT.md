# VikingsChess Deployment Guide

This document explains how to build and deploy native executables for VikingsChess across different platforms.

## Build System Overview

The project uses Gradle with jpackage to create native executables that don't require Java to be installed on the target system.

### Build Tasks

1. **`./gradlew build`** - Compiles the Java code and creates the JAR file
2. **`./gradlew runtime`** - Creates a custom JRE with only the required modules
3. **`./gradlew jpackage`** - Creates native installers (.exe, .dmg, .deb)
4. **`./gradlew jpackagePortable`** - Creates portable app bundles (for development/testing)

### Platform-Specific Outputs

- **Windows**: `VikingsChess-1.0.1.exe` (installer)
- **macOS**: `VikingsChess-1.0.1.dmg` (disk image installer)
- **Linux**: `VikingsChess_1.0.1_amd64.deb` (Debian package)

## GitHub Actions Workflow

The automated build process is configured in `.github/workflows/release.yml`:

1. Triggers on release publication
2. Builds on all three platforms (Ubuntu, Windows, macOS)
3. Creates native executables for each platform
4. Automatically uploads them to the GitHub release page

## Manual Deployment

To manually create a release with executables:

1. **Create a new release on GitHub:**
   ```bash
   gh release create v1.0.2 --title "VikingsChess v1.0.2" --notes "Release notes here"
   ```

2. **The GitHub Actions will automatically build and upload the executables**

## Local Testing

To test the build process locally:

```bash
# Build everything
./gradlew clean build runtime jpackage

# Check the output
ls -la build/dist/

# Test the executable (macOS example)
open build/dist/VikingsChess-1.0.1.dmg
```

## Icons and Branding

Place platform-specific icons in the `packaging/` directory:
- `icon.ico` for Windows (multi-resolution ICO file)
- `icon.icns` for macOS (icon bundle)
- `icon.png` for Linux (256x256 recommended)

## Dependencies

- Java 23 (for building)
- jpackage (included with JDK 14+)
- Platform-specific packaging tools are handled automatically

## File Sizes

The native executables are approximately 25-30MB each because they include:
- Your application code
- Custom JRE with only required modules (java.base, java.desktop)
- Native platform integration

This ensures users can run the game without installing Java separately.
