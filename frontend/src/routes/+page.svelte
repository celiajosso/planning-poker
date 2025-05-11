<script lang="ts">
	import { goto } from "$app/navigation";
	import { Game } from "$lib/Game.svelte";
	import randomName from "@scaleway/random-name";
	import { RoomDTO } from "$lib/RoomDTO";
	import { UserDTO } from "$lib/UserDTO";
	import { WebSocketManager } from "$lib/WebsocketManager";

	import { Input } from "$lib/components/ui/input/index.js";
	import { Button } from "$lib/components/ui/button/index.js";

	let roomName = $state("");
	let roomId = $state("");
	let userName = $state(randomName(""));

	async function create(e: SubmitEvent) {
		e.preventDefault();
		await WebSocketManager.createSocket();
		Game.createRoom(new UserDTO(userName, ""), new RoomDTO("", roomName));
		goto("/room");
	}

	async function join(e: SubmitEvent) {
		e.preventDefault();
		await WebSocketManager.createSocket();
		Game.joinRoom(new UserDTO(userName, roomId));
		goto("/room");
	}
</script>

<div
	class="w-dvw p-2 text-center text-[#333] flex flex-col h-dvh items-center justify-center bg-[#f4f4f9] gap-4"
>
	<h1 class="text-4xl font-bold">Welcome to Planning Poker</h1>
	<p class="text-lg my-4">
		Collaborate and estimate tasks efficiently with your team.
	</p>
	<div class="flex gap-8 p-8 rounded-2xl bg-main-900 border border-gray-400">
		<form class="flex-1" onsubmit={create}>
			<h2 class="text-center text-xl mb-8">Create a room</h2>
			<Input
				placeholder="Pseudonym"
				name="pseudonym"
				bind:value={userName}
				required
				class="text-main-100 bg-main-900 w-full px-4 py-2 mb-6 text-sm outline-0 focus:border rounded-lg focus:border-main-800"
			/>
			<Button type="submit">Create</Button>
		</form>
		<div class="border border-gray-400"></div>
		<form class="flex-1" onsubmit={join}>
			<h2 class="text-center text-xl mb-8">Join a room</h2>
			<Input
				placeholder="Pseudonym"
				required
				bind:value={userName}
				name="pseudonym"
				class="text-main-100 bg-main-900 w-full px-4 py-2 mb-6 text-sm outline-0 focus:border rounded-lg focus:border-main-800"
			/>
			<Input
				placeholder="RoomId"
				bind:value={roomId}
				required
				class="text-main-100 bg-main-900 w-full px-4 py-2 mb-6 text-sm outline-0 focus:border rounded-lg focus:border-main-800"
			/>
			<Button type="submit">Join</Button>
		</form>
	</div>
	<a href="/about" class="text-sm">Learn More</a>
</div>
