<script lang="ts">
	import { Icon } from "@steeze-ui/svelte-icon";

	let {
		onclick = undefined,
		icon,
		classBtn = "",
		size,
		theme = "solid",
	} = $props();
</script>

<button
	{onclick}
	class="{classBtn} backdrop:border p-1 hover:bg-gray-200 rounded-md text-gray-700"
>
	<Icon class="size-{size} color-gray-800 " src={icon} {theme} />
</button>
