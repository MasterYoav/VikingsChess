# Packaging Icons

This directory contains the application icons for different platforms:

- `icon.ico` - Windows icon (16x16, 32x32, 48x48, 256x256 pixels)
- `icon.icns` - macOS icon bundle
- `icon.png` - Linux icon (preferably 256x256 pixels)

## Creating Icons

### Windows (.ico)
You can use online converters or tools like GIMP to create .ico files from PNG images.

### macOS (.icns)
Use the `iconutil` command on macOS:
```bash
# Create a temporary iconset directory
mkdir icon.iconset
# Add different sizes (16x16, 32x32, 128x128, 256x256, 512x512)
# Then convert to .icns
iconutil -c icns icon.iconset
```

### Linux (.png)
A simple PNG file works fine for Linux applications.

For now, placeholder icons will be created automatically if these files don't exist.
