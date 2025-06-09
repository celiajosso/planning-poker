<script lang="ts">
  import { Game } from "$lib/Game.svelte";
  import { onMount } from "svelte";
  import { goto } from "$app/navigation";
  import { Icon } from "@steeze-ui/svelte-icon";
  import { WebSocketManager } from "$lib/WebsocketManager";
  import randomName from "@scaleway/random-name";

  import { isLogged } from "$lib/utils";

  import * as Tabs from "$lib/components/ui/tabs/index.js";
  import * as Tooltip from "$lib/components/ui/tooltip/index.js";
  import { Button } from "$lib/components/ui/button/index.js";
  import { Input } from "$lib/components/ui/input/index.js";

  import {
    InformationCircle,
    LockClosed,
    CheckCircle,
    ArchiveBox,
  } from "@steeze-ui/heroicons";
  import ButtonIcon from "$lib/ButtonIcon.svelte";

  let userName = $state(""); // change later when we will have the user entity
  let roomName = $state("");
  let roomId = $state("");

  if (!$isLogged) {
    userName = randomName("");
  }

  onMount(() => {
    const urlParams = new URLSearchParams(window.location.search);
    roomId = urlParams.get("roomId") ?? "";
  });

  async function create(e: SubmitEvent) {
    e.preventDefault();
    await WebSocketManager.createSocket();
    Game.createRoom(
      { id: "", username: userName, role: "Player", card: -1, roomId: "" },
      { id: "", name: roomName, stories: [], storySelected: null },
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
  {#if $isLogged}
    <div class="absolute top-4 right-4">
      <ButtonIcon
        onclick={() => {
          goto("/history");
        }}
        icon={ArchiveBox}
        size="size-8"
        theme="outline"
      />
    </div>
  {/if}

  <h1 class="text-4xl font-bold">Welcome to Planning Poker</h1>
  <p class="text-lg my-4">
    Collaborate and estimate tasks efficiently with your team.
  </p>
  {#if $isLogged}
    <div
      class="bg-green-100 border border-green-300 text-green-800 px-4 py-2 rounded-md mb-4 w-full max-w-lg"
    >
      <div class="flex items-center justify-center gap-2 text-sm">
        <Icon class="size-5" src={CheckCircle} theme="outline" />
        <p>You are logged in as <strong>{userName}</strong></p>
      </div>
    </div>
  {:else}
    <div
      class="bg-yellow-100 border border-yellow-300 text-yellow-800 px-4 py-2 rounded-md mb-4 w-full max-w-lg"
    >
      <div class="flex items-center justify-center gap-1 text-sm">
        <Icon class="size-5" src={LockClosed} theme="outline" />
        Please <a href="/login" class="underline font-medium">log in</a> to save
        your estimations and access your history
      </div>
    </div>
  {/if}

  <Tabs.Root value="join-room" class="w-3/4 max-w-lg mt-6">
    <Tabs.List class="w-full flex bg-gray-200  justify-between">
      <Tabs.Trigger class="font-semibold bg-white mr-1 flex-1" value="join-room"
        >Join a room</Tabs.Trigger
      >
      <Tabs.Trigger class="font-semibold bg-white flex-1" value="create-room"
        >Create a room</Tabs.Trigger
      >
    </Tabs.List>
    <Tabs.Content value="join-room">
      <form
        class="flex-1 m-4 border border-gray-300 p-2 rounded-xl"
        onsubmit={join}
        class:h-[235px]={!$isLogged}
      >
        <h2 class="text-center text-xl mb-8 font-semibold">Join a room</h2>
        {#if !$isLogged}
          <Input
            placeholder="Pseudonym"
            required
            bind:value={userName}
            name="pseudonym"
            class="text-main-100 bg-main-900 w-full px-4 py-2 mb-6 text-sm outline-0 focus:border rounded-lg focus:border-main-800"
          />
        {/if}
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
      ><form class="flex-1 m-4" onsubmit={create}>
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

<Tooltip.Provider>
  <Tooltip.Root>
    <Tooltip.Trigger class="absolute right-2 bottom-2">
      <Icon
        class="color-gray-800 size-5"
        src={InformationCircle}
        theme="solid"
      />
    </Tooltip.Trigger>
    <Tooltip.Content>
      Build : {import.meta.env.VITE_BUILD_DATE}
    </Tooltip.Content>
  </Tooltip.Root>
</Tooltip.Provider>
