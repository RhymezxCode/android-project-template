#!/usr/bin/env bash
set -euo pipefail

project_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "${project_dir}"

command -v java >/dev/null 2>&1 || {
  echo "Java is missing. Install JDK 17 or newer." >&2
  exit 1
}

sdk_dir="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-}}"
if [[ -z "${sdk_dir}" || ! -d "${sdk_dir}" ]]; then
  echo "Set ANDROID_HOME or ANDROID_SDK_ROOT to an installed Android SDK." >&2
  exit 1
fi

chmod +x gradlew scripts/*.sh
./gradlew --version
echo "Environment ready. Run ./scripts/verify.sh next."
