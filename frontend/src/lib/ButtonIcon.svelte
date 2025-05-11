<script lang="ts">
	let { onclick = undefined, children, classes = "" } = $props();
</script>

<button
	{onclick}
	class="{classes} backdrop:border p-1 hover:bg-gray-200 rounded-md text-gray-700"
>
	{@render children()}
</button>
