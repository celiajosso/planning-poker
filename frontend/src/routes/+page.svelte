<script lang="ts">
	import { goto } from "$app/navigation";
	import { Game } from "$lib/Game.svelte";
	import randomName from "@scaleway/random-name";
	import { RoomDTO } from "$lib/RoomDTO";
	import { UserDTO } from "$lib/UserDTO";
	import { WebSocketManager } from "$lib/WebsocketManager";

	import { Input } from "$lib/components/ui/input/index.js";
	import { Button } from "$lib/components/ui/button/index.js";

	import { onMount } from "svelte";

	import { Icon } from "@steeze-ui/svelte-icon";
	import {
		ArrowPath,
		ArrowRightEndOnRectangle,
		ChevronDoubleLeft,
		ArrowDownTray,
		ArrowUpTray,
		Plus,
		Trash,
		PaperAirplane,
		PencilSquare,
		Check,
		Share,
		Square2Stack,
	} from "@steeze-ui/heroicons";

	import { Label } from "$lib/components/ui/label/index.js";
	import * as Table from "$lib/components/ui/table/index.js";
	import { Checkbox } from "$lib/components/ui/checkbox/index.js";
	import * as Tooltip from "$lib/components/ui/tooltip/index.js";
	import * as Tabs from "$lib/components/ui/tabs/index.js";
	import ButtonIcon from "$lib/ButtonIcon.svelte";
	import { ScrollArea } from "$lib/components/ui/scroll-area/index.js";
	import * as Dialog from "$lib/components/ui/dialog/index.js";
	import * as AlertDialog from "$lib/components/ui/alert-dialog/index.js";
	import * as DropdownMenu from "$lib/components/ui/dropdown-menu/index.js";

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
	class="w-dvw p-2 text-center text-[#333] flex flex-col h-dvh items-center justify-center bg-[#f4f4f9]"
>
	<h1 class="text-4xl font-bold">Welcome to Planning Poker</h1>
	<p class="text-lg my-4">
		Collaborate and estimate tasks efficiently with your team.
	</p>

	<Tabs.Root value="join-room" class="w-3/4 max-w-lg mt-6">
		<Tabs.List class="w-full flex bg-gray-200  justify-between">
			<Tabs.Trigger
				class="font-semibold bg-white mr-1 flex-1"
				value="join-room">Join a room</Tabs.Trigger
			>
			<Tabs.Trigger
				class="font-semibold bg-white flex-1"
				value="create-room">Create a room</Tabs.Trigger
			>
		</Tabs.List>
		<Tabs.Content value="join-room"
			><form
				class="flex-1 h-[250px] m-4 border border-gray-300 p-2 rounded-xl"
				onsubmit={join}
			>
				<h2 class="text-center text-xl mb-8 font-semibold">
					Join a room
				</h2>
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
			</form></Tabs.Content
		>

		<Tabs.Content value="create-room"
			><form class="flex-1 h-[250px] m-4" onsubmit={create}>
				<div class="border border-gray-300 p-2 rounded-xl">
					<h2 class="text-center text-xl mb-8 font-semibold">
						Create a room
					</h2>
					<Input
						placeholder="Pseudonym"
						name="pseudonym"
						bind:value={userName}
						required
						class="text-main-100 bg-main-900 w-full px-4 py-2 mb-6 text-sm outline-0 focus:border rounded-lg focus:border-main-800"
					/>
					<Button type="submit">Create</Button>
				</div>
			</form></Tabs.Content
		>
	</Tabs.Root>
	<a href="/about" class="text-sm">Learn More</a>
</div>
