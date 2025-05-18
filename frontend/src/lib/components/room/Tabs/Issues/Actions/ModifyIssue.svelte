<script lang="ts">
  import { Game } from "$lib/Game.svelte";
  import { PencilSquare } from "@steeze-ui/heroicons";

  import * as Tooltip from "$lib/components/ui/tooltip/index.js";
  import * as Dialog from "$lib/components/ui/dialog/index.js";
  import * as Select from "$lib/components/ui/select/index.js";
  import { Button } from "$lib/components/ui/button/index.js";
  import { Label } from "$lib/components/ui/label";
  import { Input } from "$lib/components/ui/input";
  import { ScrollArea } from "$lib/components/ui/scroll-area";
  import ButtonIcon from "$lib/ButtonIcon.svelte";

  import { scores } from "../../../../../../routes/room/script";

  let { isModifyOpen, selectedIssue, issue } = $props();
</script>

<Tooltip.Root>
  <Tooltip.Trigger>
    <ButtonIcon
      onclick={() => {
        selectedIssue = issue;
        isModifyOpen = true;
      }}
      icon={PencilSquare}
      size="size-5"
      theme="outline"
    ></ButtonIcon>
  </Tooltip.Trigger>
  <Tooltip.Content>
    <p>Modify</p>
  </Tooltip.Content>
</Tooltip.Root>
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
