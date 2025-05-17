<script lang="ts">
  import { Game } from "$lib/Game.svelte";
  import { Icon } from "@steeze-ui/svelte-icon";
  import { Plus } from "@steeze-ui/heroicons";

  import * as Dialog from "$lib/components/ui/dialog/index.js";
  import * as Select from "$lib/components/ui/select/index.js";
  import { Button } from "$lib/components/ui/button/index.js";
  import { Label } from "$lib/components/ui/label";
  import { Input } from "$lib/components/ui/input";
  import { ScrollArea } from "$lib/components/ui/scroll-area";

  import { scores } from "../../../../../routes/room/script";

  let { isAddOpen } = $props();
</script>

<Dialog.Root bind:open={isAddOpen}>
  <form
    onsubmit={(e) => {
      Game.createStory(e);
      isAddOpen = false;
    }}
  >
    <Dialog.Trigger>
      <Button
        onclick={() => (isAddOpen = true)}
        class="outline flex flex-row gap-1 p-4 mx-auto m-2"
      >
        <Icon class="color-gray-800 size-5" src={Plus} theme="solid" />
        <div>Add an issue</div>
      </Button>
    </Dialog.Trigger>
    <Dialog.Content class="sm:max-w-[425px]">
      <Dialog.Header>
        <Dialog.Title>Add an issue</Dialog.Title>
        <Dialog.Description>
          Add an issue here. Click save when you're done.
        </Dialog.Description>
      </Dialog.Header>
      <div class="grid gap-4 py-4">
        <div class="grid grid-cols-4 items-center gap-4">
          <Label for="title" class="text-right">Title</Label>
          <Input id="title" name="title" class="col-span-3" required />
        </div>
        <div class="grid grid-cols-4 items-center gap-4">
          <Label for="description" class="text-right">Description</Label>
          <Input
            id="description"
            name="description"
            class="col-span-3"
            required
          />
        </div>
        <div class="grid grid-cols-4 items-center gap-4">
          <Label for="issue-description" class="text-right">Score</Label>
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
    </Dialog.Content>
  </form></Dialog.Root
>
