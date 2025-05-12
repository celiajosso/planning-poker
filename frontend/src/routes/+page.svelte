<script lang="ts">
  import { goto } from "$app/navigation";
  import { Game } from "$lib/Game.svelte";
  import randomName from "@scaleway/random-name";
  import { WebSocketManager } from "$lib/WebsocketManager";
  import { Input } from "$lib/components/ui/input/index.js";
  import { Button } from "$lib/components/ui/button/index.js";
  import * as Tabs from "$lib/components/ui/tabs/index.js";

  let roomName = $state("");
  let roomId = $state("");
  let userName = $state(randomName(""));

  async function create(e: SubmitEvent) {
    e.preventDefault();
    await WebSocketManager.createSocket();
    Game.createRoom(
      { id: "", username: userName, role: "Player", card: -1, roomId: "" },
      { id: "", name: roomName, stories: [], storySelected: null }
    );
    await goto("/room");
  }

  async function join(e: SubmitEvent) {
    e.preventDefault();
    await WebSocketManager.createSocket();
    Game.joinRoom({
      id: "",
      username: userName,
      role: "Player",
      card: -1,
      roomId: roomId,
    });
    await goto("/room");
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
      <Tabs.Trigger class="font-semibold bg-white mr-1 flex-1" value="join-room"
        >Join a room</Tabs.Trigger
      >
      <Tabs.Trigger class="font-semibold bg-white flex-1" value="create-room"
        >Create a room</Tabs.Trigger
      >
    </Tabs.List>
    <Tabs.Content value="join-room"
      ><form
        class="flex-1 h-[250px] m-4 border border-gray-300 p-2 rounded-xl"
        onsubmit={join}
      >
        <h2 class="text-center text-xl mb-8 font-semibold">Join a room</h2>
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
          <h2 class="text-center text-xl mb-8 font-semibold">Create a room</h2>
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
