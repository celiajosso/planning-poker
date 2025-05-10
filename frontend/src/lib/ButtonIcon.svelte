<script lang="ts">
	let { onclick = undefined, children } = $props();
</script>

<button {onclick} class="border p-1 hover:bg-gray-200 rounded-md text-gray-700">
	{@render children()}
</button>
