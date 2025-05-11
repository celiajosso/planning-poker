import adapter from '@sveltejs/adapter-static';
import { vitePreprocess } from '@sveltejs/vite-plugin-svelte';

const config = {
	preprocess: vitePreprocess(),
	kit: { adapter: adapter(), 
		alias: {
			"@/*": "./src/lib/*",
    	},
	},
	vitePlugin: {
		experimental: {
		inspector: true
		},
		include: [/\.svelte$/, /node_modules\/@lucide\/svelte\/.*\.svelte$/]
  }
};

export default config;
