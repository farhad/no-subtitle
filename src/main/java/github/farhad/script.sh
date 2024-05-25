#!/bin/bash

nojunk() {
    local path="$1"

    if [[ ! -e "$path" ]]; then
        echo "path not found"
        exit 1
    elif [[ ! -d "$path" ]]; then
        echo "path is not a directory"
        exit 1
    else
        for file in "$path"/*; do
            if [[ -d "$file" ]]; then
                traverse_directory "$file"
            else
                check_and_delete_if_subtitle "$file"
                check_and_delete_hidden_junk "$file"
                check_and_delete_ds_store "$file"
            fi
        done
    fi
}

check_and_delete_if_subtitle() {
    local file="$1"
    local extension="${file##*.}"

    if [[ "$extension" == "srt" || "$extension" == "vtt" ]]; then
        if rm "$file"; then
            echo "DELETED SUBTITLE: $(basename "$file")"
        fi
    fi
}

check_and_delete_hidden_junk() {
    local file="$1"

    if [[ "$(basename "$file")" == ._* && -f "$file" && $(stat -c %A "$file") =~ ^-[a-zA-Z] ]]; then
        if rm "$file"; then
            echo "DELETED JUNK: $(basename "$file")"
        fi
    fi
}

check_and_delete_ds_store() {
    local file="$1"

    if [[ "$(basename "$file")" == ".DS_Store" ]]; then
        if rm "$file"; then
            echo "DELETED DS Store: $(basename "$file")"
        fi
    fi
}

## Main execution starts here
#path="/Users/farhad/Downloads"
nojunk "$path"