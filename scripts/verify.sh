#!/usr/bin/env bash
set -euo pipefail

project_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "${project_dir}"

./gradlew \
  test \
  lint \
  detekt \
  lintKotlin \
  koverXmlReport \
  koverVerify \
  assembleDebug
