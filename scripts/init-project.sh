#!/usr/bin/env bash
set -euo pipefail

if [[ $# -ne 2 ]]; then
  echo "Usage: $0 \"Project Name\" com.example.application" >&2
  exit 1
fi

project_name="$1"
package_name="$2"

if [[ ! "${project_name}" =~ ^[A-Za-z][A-Za-z0-9_\ -]*$ ]]; then
  echo "Project name must start with a letter and contain only letters, numbers, spaces, _ or -." >&2
  exit 1
fi
if [[ ! "${package_name}" =~ ^[a-z][a-z0-9_]*(\.[a-z][a-z0-9_]*)+$ ]]; then
  echo "Package must look like com.example.application and use lowercase segments." >&2
  exit 1
fi

project_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "${project_dir}"

class_prefix="$(printf '%s' "${project_name}" | tr -cd '[:alnum:]')"
old_package="com.example.modularapp"
old_path="com/example/modularapp"
new_path="$(printf '%s' "$package_name" | tr '.' '/')"

while IFS= read -r -d '' file; do
  perl -pi -e \
    "s|\\Q${old_package}\\E|${package_name}|g; \
     s|\\QModularAndroidTemplate\\E|${class_prefix}|g; \
     s|\\QModular Android Starter\\E|${project_name}|g; \
     s|\\QStarterApplication\\E|${class_prefix}Application|g; \
     s|\\QStarterTheme\\E|${class_prefix}Theme|g; \
     s|\\QStarterApp\\E|${class_prefix}App|g; \
     s|\\QTheme.Starter\\E|Theme.${class_prefix}|g" \
    "${file}"
done < <(
  find . -type f \
    \( -name '*.kt' -o -name '*.kts' -o -name '*.xml' -o -name '*.toml' \
       -o -name '*.md' -o -name '*.yml' -o -name '*.yaml' -o -name '*.properties' \
       -o -name '*.gradle' \) \
    ! -path './.git/*' \
    ! -path './.gradle/*' \
    ! -path './*/build/*' \
    ! -path './scripts/init-project.sh' \
    ! -path './scripts/init-project.ps1' \
    -print0
)

while IFS= read -r -d '' old_dir; do
  source_root="${old_dir%/${old_path}}"
  new_dir="${source_root}/${new_path}"
  mkdir -p "$(dirname "${new_dir}")"
  mv "${old_dir}" "${new_dir}"
  rmdir "${source_root}/com/example" 2>/dev/null || true
done < <(
  find . -depth -type d -path "*/${old_path}" \
    ! -path './.gradle/*' \
    ! -path './*/build/*' \
    -print0
)

while IFS= read -r -d '' file; do
  mv "${file}" "$(dirname "${file}")/${class_prefix}Application.kt"
done < <(find . -type f -name 'StarterApplication.kt' ! -path './*/build/*' -print0)

while IFS= read -r -d '' file; do
  mv "${file}" "$(dirname "${file}")/${class_prefix}Theme.kt"
done < <(find . -type f -name 'StarterTheme.kt' ! -path './*/build/*' -print0)

echo "Initialized ${project_name} (${package_name})."
echo "Run ./scripts/setup.sh and ./scripts/verify.sh, then commit the generated changes."
