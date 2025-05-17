<script lang="ts">
  import QrCode from "svelte-qrcode";
  import { onMount } from "svelte";
  import { goto } from "$app/navigation";
  import { Game } from "$lib/Game.svelte";
  import { WebSocketManager } from "$lib/WebsocketManager";
  import * as Select from "$lib/components/ui/select/index.js";
  import { Icon } from "@steeze-ui/svelte-icon";
  import {
    ArrowPath,
    ArrowRightEndOnRectangle,
    Share,
    Square2Stack,
  } from "@steeze-ui/heroicons";

  import * as Tooltip from "$lib/components/ui/tooltip/index.js";
  import * as Dialog from "$lib/components/ui/dialog/index.js";
  import * as DropdownMenu from "$lib/components/ui/dropdown-menu/index.js";
  import { Button } from "$lib/components/ui/button/index.js";
  import { Input } from "$lib/components/ui/input/index.js";
  import { Label } from "$lib/components/ui/label/index.js";
  import { ScrollArea } from "$lib/components/ui/scroll-area/index.js";
  import ButtonIcon from "$lib/ButtonIcon.svelte";
  import { copyRoomIdClipboard, copyRoomLinkClipboard, scores } from "./+page";
  import Sheet from "./Sheet.svelte";

  let selectedIssue = null;

  let isModifyOpen = $state(false);
  let isAddOpen = $state(false);

  let canvas: HTMLCanvasElement;

  let loaded = $state(false);

  onMount(async () => {
    if (WebSocketManager.socket === undefined) {
      goto("/");
    } else {
      await Game.init(canvas);
      loaded = true;
    }
  });
</script>

<canvas bind:this={canvas} class="h-dvh w-dvw"></canvas>
{#if loaded}
  <Sheet {isModifyOpen} {selectedIssue} {isAddOpen}></Sheet>

  <div class="absolute left-0 bottom-4 flex justify-center items-center w-full">
    <div
      class="bg-white rounded-xl flex px-4 py-2 gap-4 flex-col border-2 border-gray-300"
    >
      <div class="flex items-center gap-4 justify-center text-gray-700 mt-1">
        <Tooltip.Root>
          <Tooltip.Trigger>
            <ButtonIcon
              onclick={Game.restart}
              icon={ArrowPath}
              size="6"
              theme="solid"
            ></ButtonIcon>
          </Tooltip.Trigger>
          <Tooltip.Content>
            <p>Reset animation</p>
          </Tooltip.Content>
        </Tooltip.Root>

        <Tooltip.Root>
          <Tooltip.Trigger>
            <ButtonIcon
              onclick={() => {
                Game.quitRoom();
                goto("/");
              }}
              icon={ArrowRightEndOnRectangle}
              size="6"
              theme="solid"
            ></ButtonIcon>
          </Tooltip.Trigger>
          <Tooltip.Content>
            <p>Exit Room</p>
          </Tooltip.Content>
        </Tooltip.Root>

        <DropdownMenu.Root>
          <DropdownMenu.Trigger>
            <Tooltip.Root>
              <Tooltip.Trigger>
                <ButtonIcon icon={Share} size="6" theme="outline"></ButtonIcon>
              </Tooltip.Trigger>
              <Tooltip.Content>
                <p>Share Room</p>
              </Tooltip.Content>
            </Tooltip.Root>
          </DropdownMenu.Trigger>
          <DropdownMenu.Content class="w-40">
            <DropdownMenu.Label>Share Room</DropdownMenu.Label>
            <DropdownMenu.Separator />
            <DropdownMenu.Group>
              <DropdownMenu.Item>
                <QrCode
                  value={`${window.location.origin}?roomId=${Game.storage.room.id}`}
                />
              </DropdownMenu.Item>
              <DropdownMenu.Item onclick={copyRoomIdClipboard}>
                Share Id
                <DropdownMenu.Shortcut
                  ><Icon
                    class="color-gray-800 size-4"
                    src={Square2Stack}
                    theme="outline"
                  /></DropdownMenu.Shortcut
                >
              </DropdownMenu.Item>
              <DropdownMenu.Item onclick={copyRoomLinkClipboard}>
                Share link
                <DropdownMenu.Shortcut
                  ><Icon
                    class="color-gray-800 size-4"
                    src={Square2Stack}
                    theme="outline"
                  /></DropdownMenu.Shortcut
                >
              </DropdownMenu.Item>
            </DropdownMenu.Group>
          </DropdownMenu.Content>
        </DropdownMenu.Root>
      </div>
    </div>
    <Dialog.Root bind:open={isModifyOpen}>
      <Dialog.Content class="sm:max-w-[425px]">
        <form
          onsubmit={(e) => {
            console.log(e);
            Game.updateStory(selectedIssue!.id, e);
            isModifyOpen = false;
          }}
        >
          <Dialog.Header>
            <Dialog.Title>Edit issue</Dialog.Title>
            <Dialog.Description>
              Make changes to your issue here. Click save when you're done.
            </Dialog.Description>
          </Dialog.Header>
          <div class="grid gap-4 py-4">
            <div class="grid grid-cols-4 items-center gap-4">
              <Label for="title" class="text-right">Title</Label>
              <Input
                name="title"
                id="title"
                value={selectedIssue!.title}
                class="col-span-3"
                required
              />
            </div>
            <div class="grid grid-cols-4 items-center gap-4">
              <Label for="description" class="text-right">Description</Label>
              <Input
                id="description"
                name="description"
                value={selectedIssue!.description}
                class="col-span-3"
                required
              />
            </div>
            <div class="grid grid-cols-4 items-center gap-4">
              <Label for="finalEstimate" class="text-right">Score</Label>
              <Select.Root>
                <Select.Trigger class="w-[100px]">
                  <Select.Value placeholder="Score" />
                </Select.Trigger>
                <Select.Content>
                  <Select.Group>
                    <ScrollArea class="h-20">
                      {#each scores as score}
                        <Select.Item value={score.value} label={score.value}
                          >{score.value}</Select.Item
                        >
                      {/each}
                    </ScrollArea>
                  </Select.Group>
                </Select.Content>
                <Select.Input name="score" />
              </Select.Root>
            </div>
          </div>
          <Dialog.Footer>
            <Button type="submit">Save changes</Button>
          </Dialog.Footer>
        </form>
      </Dialog.Content>
    </Dialog.Root>
  </div>
{/if}
