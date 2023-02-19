# Image2Terminal

Uses Unicode block elements in combination with ANSI escape codes to display images in the terminal.

```
█ ▄ ▀
```

Provide a pixel art image like these:
![Smol2](examples/trashpanda2.png?raw=true)
![Smol1](examples/trashpanda.png?raw=true)

And get this:  
![Terminal](examples/trashpandaOutput.png?raw=true)

## Usage
Just run 
```bash
java -jar Image2Terminal.jar input.png
```

And your converted image will be saved to `input.bash` from which you can copy the ansi or just run the bash script directly from your own program.