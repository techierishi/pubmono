import { execSync } from 'child_process';
import { mkdirSync, copyFileSync, existsSync, readdirSync, statSync } from 'fs';
import { resolve, dirname } from 'path';
import { fileURLToPath } from 'url';

const __dirname = dirname(fileURLToPath(import.meta.url));
const root = resolve(__dirname, '..');
const dist = resolve(root, 'dist');

console.log('🚀 Building Better Action Chrome Extension...');

// 1. Ensure target directories exist
const dirs = ['background', 'content', 'icons', 'settings', 'sidepanel', 'tab'];
for (const d of dirs) {
  mkdirSync(resolve(dist, d), { recursive: true });
}

// 2. Build Vite components (Side Panel & Tab UI)
console.log('📦 Running Vite build for sidepanel and tab...');
execSync('npx vite build', { cwd: root, stdio: 'inherit' });

// 3. Bundle content script (IIFE format for browser page injection)
console.log('📦 Bundling content script...');
execSync(
  'npx esbuild src/content/content.js --bundle --outfile=dist/content/content.js --format=iife --target=chrome114',
  { cwd: root, stdio: 'inherit' }
);

// 4. Bundle background service worker (ESM format)
console.log('📦 Bundling background service worker...');
execSync(
  'npx esbuild src/background/background.js --bundle --outfile=dist/background/background.js --format=esm --target=es2022',
  { cwd: root, stdio: 'inherit' }
);

// 5. Copy manifest, styles, and assets
console.log('📄 Copying manifest and assets...');
copyFileSync(resolve(root, 'manifest.json'), resolve(dist, 'manifest.json'));
copyFileSync(resolve(root, 'src/content/content.css'), resolve(dist, 'content/content.css'));

if (existsSync(resolve(root, 'cors_rules.json'))) {
  copyFileSync(resolve(root, 'cors_rules.json'), resolve(dist, 'cors_rules.json'));
}

// 6. Copy Settings files
const settingsFiles = ['settings.html', 'settings.js', 'settings.css'];
for (const f of settingsFiles) {
  const srcFile = resolve(root, 'src/settings', f);
  if (existsSync(srcFile)) {
    copyFileSync(srcFile, resolve(dist, 'settings', f));
  }
}

// 7. Ensure icons are generated and copied
const iconSizes = [16, 32, 48, 128];
const needIcons = iconSizes.some(s => !existsSync(resolve(root, `icons/icon${s}.png`)));
if (needIcons) {
  console.log('🎨 Generating extension icons...');
  execSync('node scripts/generate-icons.js', { cwd: root, stdio: 'inherit' });
}

for (const size of iconSizes) {
  const iconPath = resolve(root, `icons/icon${size}.png`);
  if (existsSync(iconPath)) {
    copyFileSync(iconPath, resolve(dist, `icons/icon${size}.png`));
  }
}

// 8. Verification and summary
console.log('\n🔍 Verifying build output in dist/:');
const requiredFiles = [
  'manifest.json',
  'background/background.js',
  'sidepanel/index.html',
  'content/content.js',
  'content/content.css',
  'icons/icon16.png',
  'icons/icon32.png',
  'icons/icon48.png',
  'icons/icon128.png',
  'settings/settings.html'
];

let allOk = true;
for (const req of requiredFiles) {
  const fullPath = resolve(dist, req);
  if (existsSync(fullPath)) {
    const size = (statSync(fullPath).size / 1024).toFixed(1);
    console.log(`  ✓ ${req.padEnd(30)} (${size} kB)`);
  } else {
    console.error(`  ✗ MISSING: ${req}`);
    allOk = false;
  }
}

if (!allOk) {
  console.error('\n❌ Build verification failed! Some files are missing in dist/');
  process.exit(1);
}

console.log('\n✨ Build complete! Extension is ready in:');
console.log(`   ${dist}`);
console.log('👉 To load in Chrome: Navigate to chrome://extensions/ -> "Load unpacked" -> select the dist/ folder.\n');
