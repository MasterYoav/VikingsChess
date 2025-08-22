#!/bin/bash
echo "Starting VikingsChess..."
cd "$(dirname "$0")"
if command -v java &> /dev/null; then
    java -jar "VikingsChess-1.0.0.jar"
else
    echo ""
    echo "Error: Java is not installed or not found in PATH."
    echo "Please install Java using your package manager:"
    echo "  Ubuntu/Debian: sudo apt install openjdk-17-jre"
    echo "  Fedora/RHEL:   sudo dnf install java-17-openjdk"
    echo "  Arch Linux:    sudo pacman -S jre-openjdk"
    echo ""
    read -p "Press Enter to continue..."
fi
