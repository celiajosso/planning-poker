<script lang="ts">
	import { onMount } from 'svelte';
	import store from '$lib/socket';

	let message;
	let messages = [];

	onMount(() => {
		store.subscribe((currentMessage) => {
			messages = [...messages, currentMessage];
		});
	});

	function onSendMessage() {
		if (message.length > 0) {
			store.sendMessage(message);
			message = '';
		}
	}
</script>

<h1>Hello Chat</h1>

<input type="text" bind:value={message} />
<button on:click={onSendMessage}> Send Message </button>
{#each messages as message, i}
	<p>
		{message}
	</p>
{/each}
