# Best Minecraft AntiCheat

## Overview

This AntiCheat plugin is designed to protect your Minecraft server from players using the Baritone software, which automates pathfinding and mimics player movements. It also safeguards against X-ray bypass, where players can see and interact with ores through blocks. 

Key features include:
- Detection and prevention of automated pathfinding and movement.
- Protection against illegal clicks and digs that attempt to interact with hidden ores.
- Upcoming features for detecting illegal attacks and climbing.

## Features

- **Pathfinding Detection**: Utilizes the A* algorithm to analyze and mimic Baritone's pathfinding techniques, ensuring no automated paths are followed.
- **Yaw and Pitch Checks**: Monitors player orientation to detect unnatural movements.
- **Illegal Click and Dig Detection**: Uses breadth-first search to find the shortest path between the clicked block and the player, ensuring no illegal interactions occur through blocks.
- **Anti-Xray**: Ensures that players cannot scan or interact with ores through blocks.

## Requirements

- Java 8
- Minecraft 1.16.5

## Installation

1. Download the plugin jar file.
2. Place the jar file into the `plugins` directory of your Minecraft server.
3. Restart your server to load the plugin.

## Configuration

The plugin works out-of-the-box with default settings. However, you can tweak the configuration file found in the `plugins/AntiCheat` directory for advanced settings.

## Usage

Once installed, the AntiCheat plugin will automatically start monitoring player activities and prevent any illegal actions detected.

## Development

- **Start Date**: August 2023
- **Language**: Java 8
- **Minecraft Version**: 1.16.5

## Upcoming Features

- **Illegal Attack Detection**: Identify and prevent players from performing attacks through illegal means.
- **Illegal Climb Detection**: Monitor and stop players from climbing using unauthorized methods.

## Contributing

Contributions are welcome! Please fork the repository and submit pull requests. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the CC BY-NC-ND (Attribution-NonCommercial-NoDerivs) - see the `LICENSE` file for details.

## Contact

For any questions or support, please open an issue on the GitHub repository or contact the developer at [raksenov50@gmail.com].

---

Thank you for using the Best Minecraft AntiCheat plugin! Your server's security is our top priority.
