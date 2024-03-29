# Whiptail Help

Box options:

* msgbox `<text> <height> <width>`
* yesno  `<text> <height> <width>`
* infobox `<text> <height> <width>`
* inputbox `<text> <height> <width> [init]`
* passwordbox `<text> <height> <width> [init]`
* textbox `<file> <height> <width>`
* menu `<text> <height> <width> <listheight> [tag item] ...`
* checklist `<text> <height> <width> <listheight> [tag item status]...
* radiolist `<text> <height> <width> <listheight> [tag item status]...`
* gauge `<text> <height> <width> <percent>`

```bash
pi@raspberrypi-4-1:~ $ whiptail --help
```

```text
Box options:
        --msgbox <text> <height> <width>
        --yesno  <text> <height> <width>
        --infobox <text> <height> <width>
        --inputbox <text> <height> <width> [init]
        --passwordbox <text> <height> <width> [init]
        --textbox <file> <height> <width>
        --menu <text> <height> <width> <listheight> [tag item] ...
        --checklist <text> <height> <width> <listheight> [tag item status]...
        --radiolist <text> <height> <width> <listheight> [tag item status]...
        --gauge <text> <height> <width> <percent>
Options: (depend on box-option)
        --clear                         clear screen on exit
        --defaultno                     default no button
        --default-item <string>         set default string
        --fb, --fullbuttons             use full buttons
        --nocancel                      no cancel button
        --yes-button <text>             set text of yes button
        --no-button <text>              set text of no button
        --ok-button <text>              set text of ok button
        --cancel-button <text>          set text of cancel button
        --noitem                        don't display items
        --notags                        don't display tags
        --separate-output               output one line at a time
        --output-fd <fd>                output to fd, not stdout
        --title <title>                 display title
        --backtitle <backtitle>         display backtitle
        --scrolltext                    force vertical scrollbars
        --topleft                       put window in top-left corner
        -h, --help                      print this message
        -v, --version                   print version information
```
