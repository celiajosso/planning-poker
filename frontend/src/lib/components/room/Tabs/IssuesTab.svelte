<script lang="ts">
  import { exportToCSV } from "$lib/CSV.svelte";
  import {
    ArrowDownTray,
    ArrowUpTray,
    Plus,
    Trash,
    PaperAirplane,
    PencilSquare,
  } from "@steeze-ui/heroicons";

  import * as Table from "$lib/components/ui/table/index.js";
  import * as Tabs from "$lib/components/ui/tabs/index.js";
  import * as AlertDialog from "$lib/components/ui/alert-dialog/index.js";
  import * as Tooltip from "$lib/components/ui/tooltip/index.js";
  import * as Dialog from "$lib/components/ui/dialog/index.js";
  import * as Select from "$lib/components/ui/select/index.js";
  import { handleFileChange } from "../../../../routes/room/script";
  import { Game } from "$lib/Game.svelte";
  import { Button } from "$lib/components/ui/button/index.js";
  import { Icon } from "@steeze-ui/svelte-icon";
  import { Label } from "$lib/components/ui/label";
  import { Input } from "$lib/components/ui/input";
  import { ScrollArea } from "$lib/components/ui/scroll-area";
  import ButtonIcon from "$lib/ButtonIcon.svelte";

  import { scores } from "../../../../routes/room/script";
  import ImportButton from "./Issues/ImportButton.svelte";
  import ExportButton from "./Issues/ExportButton.svelte";
  import AddIssue from "./Issues/AddIssue.svelte";

  let { isModifyOpen, selectedIssue, isAddOpen } = $props();

  let fileInput: HTMLInputElement;
</script>

<Tabs.Content value="issues">
  <div class="grid gap-2 p-5 my-5 border border-gray-300 rounded-lg">
    <div class="flex flex-row justify-between items-center">
      <div>
        <p class="font-bold text-xl">Issues</p>
        <p class="text-sm">Make changes to your User Stories.</p>
      </div>
      <div class="flex flex-row items-center h-full gap-2">
        <ImportButton />
        <ExportButton />
      </div>
    </div>
    {#if Game.storage.room.stories && Game.storage.room.stories.length != 0}
      <Table.Root>
        <Table.Header>
          <Table.Row>
            <Table.Head>Title</Table.Head>
            <Table.Head>Description</Table.Head>
            <Table.Head>Score</Table.Head>
            <Table.Head></Table.Head>
          </Table.Row>
        </Table.Header>

        <Table.Body>
          {#each Game.storage.room.stories as issue}
            <Table.Row>
              <Table.Cell>{issue.title}</Table.Cell>
              <Table.Cell>
                <p class="truncate max-w-96">
                  {issue.description}
                </p></Table.Cell
              >
              <Table.Cell>
                <!-- <Select.Root>
                        <Select.Trigger class="w-[100px]">
                          <Select.Value placeholder="Score" />
                        </Select.Trigger>
                        <Select.Content>
                          <Select.Group>
                            <ScrollArea class="h-20">
                              {#each scores as score}
                                <Select.Item
                                  value={score.value}
                                  label={score.value}>{score.value}</Select.Item
                                >
                              {/each}
                            </ScrollArea>
                          </Select.Group>
                        </Select.Content>
                        <Select.Input name="score" />
                      </Select.Root> -->
                {issue.finalEstimate}
              </Table.Cell>
              <Table.Cell>
                <div class="flex flex-row items-center h-full gap-2">
                  <AlertDialog.Root>
                    <AlertDialog.Trigger
                      ><Tooltip.Root>
                        <AlertDialog.Trigger asChild>
                          <Tooltip.Root>
                            <Tooltip.Trigger>
                              <ButtonIcon
                                icon={Trash}
                                size="size-5"
                                theme="outline"
                              ></ButtonIcon>
                            </Tooltip.Trigger>
                            <Tooltip.Content>
                              <p>Delete</p>
                            </Tooltip.Content>
                          </Tooltip.Root>
                        </AlertDialog.Trigger>

                        <Tooltip.Content>
                          <p>Delete</p>
                        </Tooltip.Content>
                      </Tooltip.Root></AlertDialog.Trigger
                    >
                    <AlertDialog.Content>
                      <AlertDialog.Header>
                        <AlertDialog.Title>Are you sure?</AlertDialog.Title>
                        <AlertDialog.Description>
                          This action cannot be undone. This will permanently
                          delete your issue.
                        </AlertDialog.Description>
                      </AlertDialog.Header>
                      <AlertDialog.Footer>
                        <AlertDialog.Cancel>Cancel</AlertDialog.Cancel>
                        <AlertDialog.Action
                          onclick={() => Game.deleteStory(issue.id)}
                          >Continue</AlertDialog.Action
                        >
                      </AlertDialog.Footer>
                    </AlertDialog.Content>
                  </AlertDialog.Root>
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
                  <Tooltip.Root>
                    <Tooltip.Trigger>
                      <ButtonIcon
                        onclick={() => Game.selectStory(issue.id)}
                        icon={PaperAirplane}
                        size="size-5"
                        theme="outline"
                      ></ButtonIcon>
                    </Tooltip.Trigger>
                    <Tooltip.Content>
                      <p>Vote</p>
                    </Tooltip.Content>
                  </Tooltip.Root>
                </div>
              </Table.Cell>
            </Table.Row>
          {/each}
        </Table.Body>
      </Table.Root>
    {:else}
      <p class="text-center w-full text-gray-500">No issue</p>
    {/if}
    <div class="flex justify-center w-full">
      <AddIssue {isAddOpen} />
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
  </div>
</Tabs.Content>
