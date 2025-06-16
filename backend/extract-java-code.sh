#!/bin/bash

# Встановлюємо змінну розширення файлів
EXT=".java"

# Файл, у який усе запишемо
OUTPUT="all-code.txt"

# Очищаємо або створюємо файл
> "$OUTPUT"

# Рекурсивний пошук і додавання коду
find . -type f -name "*$EXT" | while read file; do
  echo "===== $file =====" >> "$OUTPUT"
  cat "$file" >> "$OUTPUT"
  echo -e "\n\n" >> "$OUTPUT"
done

echo "✅ Усі файли з розширенням $EXT додано до $OUTPUT"
