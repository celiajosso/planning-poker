<script lang="ts">
	import { onMount } from 'svelte';
	import { Icon } from '@steeze-ui/svelte-icon';
	import { ArrowPath, ArrowRightEndOnRectangle } from '@steeze-ui/heroicons';
	import { Game } from '$lib/Game.svelte';
	import { goto } from '$app/navigation';
	import { WebSocketManager } from '$lib/WebsocketManager';

	let canvas: HTMLCanvasElement;

	let loaded = $state(false);

	onMount(async () => {
		if (WebSocketManager.socket === undefined) {
			goto('/');
		} else {
			await Game.init(canvas);
			loaded = true;
		}
	});
</script>

<canvas bind:this={canvas}></canvas>
{#if loaded}
	<div class="absolute left-0 bottom-4 flex justify-center items-center w-full">
		<div class="bg-white rounded-xl flex px-4 py-2 gap-4 flex-col">
			<input bind:value={Game.user.username} type="text" />
			<input bind:value={Game.room.id} type="text" />
			<div class="flex items-center justify-center">
				<button onclick={() => Game.restart()}>
					<Icon class="color-gray-900 size-8" src={ArrowPath} theme="solid" />
				</button>
				<button onclick={() => Game.quitRoom()}>
					<Icon class="color-gray-900 size-8" src={ArrowRightEndOnRectangle} theme="solid" />
				</button>
			</div>
		</div>
	</div>
{/if}
