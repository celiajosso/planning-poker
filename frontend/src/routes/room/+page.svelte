<script lang="ts">
  import { onMount } from "svelte";
  import { goto } from "$app/navigation";
  import { Game } from "$lib/Game.svelte";
  import { WebSocketManager } from "$lib/WebsocketManager";

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

  import * as Sheet from "$lib/components/ui/sheet/index.js";
  import { Button } from "$lib/components/ui/button/index.js";
  import { Input } from "$lib/components/ui/input/index.js";
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

  let checked = false;
  let selectedIssue = null;

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

  const issues = [
    {
      title: "SCRUM-1",
      description: "The User story ...",
      score: "1",
    },
    {
      title: "SCRUM-2",
      description: "The User story ...",
      score: "7",
    },
    {
      title: "SCCRUM-3",
      description: "The User story ...",
      score: "11",
    },
  ];

  let fileInput: HTMLInputElement;

  function handleButtonClick() {
    fileInput.click();
  }

  function handleFileChange(event) {
    const file = event.target.files[0];
    if (file && file.type === "text/csv") {
      console.log("CSV file selected:", file);
    } else {
      alert("Please selectt a CSV file.");
    }
  }

  // /!\ A CHANGER SELON LE FORMAT CSV DE JIRA !!!
  function exportToCSV() {
    const csvRows = [];

    const headers = ["Title", "Description", "Score"];
    csvRows.push(headers.join(","));

    for (const issue of issues) {
      const row = [issue.title, issue.description, issue.score];
      csvRows.push(
        row.map((value) => `"${value.replace(/"/g, '""')}"`).join(",")
      );
    }

    const csvContent = csvRows.join("\n");

    const blob = new Blob([csvContent], {
      type: "text/csv;charset=utf-8;",
    });
    const url = URL.createObjectURL(blob);
    const link = document.createElement("a");
    link.setAttribute("href", url);
    link.setAttribute("download", "issues_export.csv");
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    URL.revokeObjectURL(url);
  }

  import * as Select from "$lib/components/ui/select/index.js";

  const scores = Array.from({ length: 14 }, (_, i) => ({
    value: i.toString(),
    label: i.toString(),
  }));

  let value = $state("");

  function copyRoomIdClipboard() {
    navigator.clipboard.writeText(Game.storage.room.id).then(
      function () {
        console.log("Async: Copying to clipboard was successful!");
      },
      function (err) {
        console.error("Async: Could not copy text: ", err);
      }
    );
  }

  // function copyRoomLinkClipboard() {
  // 	navigator.clipboard.writeText(Game.room.link).then(
  // 		function () {
  // 			console.log("Async: Copying to clipboard was successful!");
  // 		},
  // 		function (err) {
  // 			console.error("Async: Could not copy text: ", err);
  // 		},
  // 	);
  // }
</script>

<canvas bind:this={canvas} class="h-dvh w-dvw"></canvas>
{#if loaded}
  <!-- {#if selectedIssue}
		<div class="p-4 border rounded my-4 bg-gray-100">
			<h2 class="text-xl font-bold">{selectedIssue.title}</h2>
			<p>{selectedIssue.description}</p>
		</div>
	{/if} -->
  <Sheet.Root>
    <Sheet.Trigger class="absolute top-5 right-5 color-gray-700">
      <ButtonIcon>
        <Icon
          class="color-gray-800 size-6"
          src={ChevronDoubleLeft}
          theme="solid"
        />
      </ButtonIcon>
    </Sheet.Trigger>
    <Sheet.Content side="right" class="bg-[#f4f4f9] text-gray-900">
      <Tabs.Root value="issues" class="w-full">
        <Tabs.List class=" w-full flex bg-gray-200 mt-5 justify-between">
          <Tabs.Trigger
            class="font-semibold  bg-white mr-1 flex-1"
            value="issues">Issues</Tabs.Trigger
          >
          <Tabs.Trigger
            class="font-semibold bg-white flex-1"
            value="profile-settings">Profile Settings</Tabs.Trigger
          >
        </Tabs.List>
        <Tabs.Content value="issues">
          <div class="grid gap-2 p-5 my-5 border border-gray-300 rounded-lg">
            <div class="flex flex-row justify-between items-center">
              <div>
                <p class="font-bold text-xl">Issues</p>
                <p class="text-sm">Make changes to your User Stories.</p>
              </div>
              <div class="flex flex-row items-center h-full gap-2">
                <Tooltip.Root>
                  <Tooltip.Trigger>
                    <ButtonIcon onclick={handleButtonClick}>
                      <Icon
                        class="color-gray-800 size-5"
                        src={ArrowDownTray}
                        theme="solid"
                      />
                    </ButtonIcon>
                  </Tooltip.Trigger>
                  <Tooltip.Content>
                    <p>Import CSV file</p>
                  </Tooltip.Content>
                </Tooltip.Root>
                <input
                  bind:this={fileInput}
                  type="file"
                  accept=".csv"
                  onchange={handleFileChange}
                  style="display: none;"
                />
                <Tooltip.Root>
                  <Tooltip.Trigger>
                    <ButtonIcon onclick={exportToCSV}>
                      <Icon
                        class="color-gray-800 size-5"
                        src={ArrowUpTray}
                        theme="solid"
                      />
                    </ButtonIcon>
                  </Tooltip.Trigger>
                  <Tooltip.Content>
                    <p>Export CSV file</p>
                  </Tooltip.Content>
                </Tooltip.Root>
              </div>
            </div>

            <Table.Root>
              <Table.Header>
                <Table.Row>
                  <!-- <Table.Head class="text-center">
										<Checkbox
											id="terms"
											bind:checked
											aria-labelledby="terms-label"
											class="mx-auto"
										/>
									</Table.Head> -->
                  <Table.Head>Title</Table.Head>
                  <Table.Head>Description</Table.Head>
                  <Table.Head>Score</Table.Head>
                  <Table.Head></Table.Head>
                </Table.Row>
              </Table.Header>
              <Table.Body>
                {#each Game.storage.room.stories as issue}
                  <Table.Row>
                    <!--<Table.Cell>
											 <Checkbox
												id="terms"
												bind:checked
												aria-labelledby="terms-label"
											/> 
										</Table.Cell>-->
                    <Table.Cell>{issue.title}</Table.Cell>
                    <Table.Cell>
                      <p class="truncate max-w-96">
                        {issue.description}
                      </p></Table.Cell
                    >
                    <Table.Cell>
                      <Select.Root>
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
                      </Select.Root>
                    </Table.Cell>
                    <Table.Cell>
                      <div class="flex flex-row items-center h-full gap-2">
                        <AlertDialog.Root>
                          <AlertDialog.Trigger
                            ><Tooltip.Root>
                              <AlertDialog.Trigger asChild>
                                <Tooltip.Root>
                                  <Tooltip.Trigger>
                                    <ButtonIcon>
                                      <Icon
                                        class="color-gray-800 size-5"
                                        src={Trash}
                                        theme="outline"
                                      />
                                    </ButtonIcon>
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
                              <AlertDialog.Title
                                >Are you sure?</AlertDialog.Title
                              >
                              <AlertDialog.Description>
                                This action cannot be undone. This will
                                permanently delete your issue.
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
                        <Dialog.Root>
                          <form onsubmit={(e) => Game.updateStory(issue.id, e)}>
                            <Dialog.Trigger>
                              <Tooltip.Root>
                                <Tooltip.Trigger>
                                  <ButtonIcon>
                                    <Icon
                                      class="color-gray-800 size-5"
                                      src={PencilSquare}
                                      theme="outline"
                                    />
                                  </ButtonIcon>
                                </Tooltip.Trigger>
                                <Tooltip.Content>
                                  <p>Modify</p>
                                </Tooltip.Content>
                              </Tooltip.Root>
                            </Dialog.Trigger>
                            <Dialog.Content class="sm:max-w-[425px]">
                              <Dialog.Header>
                                <Dialog.Title>Edit issue</Dialog.Title>
                                <Dialog.Description>
                                  Make changes to your issue here. Click save
                                  when you're done.
                                </Dialog.Description>
                              </Dialog.Header>
                              <div class="grid gap-4 py-4">
                                <div
                                  class="grid grid-cols-4 items-center gap-4"
                                >
                                  <Label for="title" class="text-right"
                                    >Title</Label
                                  >
                                  <Input
                                    name="title"
                                    id="title"
                                    value={issue.title}
                                    class="col-span-3"
                                  />
                                </div>
                                <div
                                  class="grid grid-cols-4 items-center gap-4"
                                >
                                  <Label for="description" class="text-right"
                                    >Description</Label
                                  >
                                  <Input
                                    id="description"
                                    name="description"
                                    value={issue.description}
                                    class="col-span-3"
                                  />
                                </div>
                                <div
                                  class="grid grid-cols-4 items-center gap-4"
                                >
                                  <Label for="finalEstimate" class="text-right"
                                    >Score</Label
                                  >
                                  <Select.Root>
                                    <Select.Trigger class="w-[100px]">
                                      <Select.Value placeholder="Score" />
                                    </Select.Trigger>
                                    <Select.Content>
                                      <Select.Group>
                                        <ScrollArea class="h-20">
                                          {#each scores as score}
                                            <Select.Item
                                              value={score.value}
                                              label={score.value}
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
                          </form>
                        </Dialog.Root>

                        <Tooltip.Root>
                          <Tooltip.Trigger>
                            <ButtonIcon
                              onclick={() => Game.selectStory(issue.id)}
                            >
                              <Icon
                                class="color-gray-800 size-5"
                                src={PaperAirplane}
                                theme="outline"
                              />
                            </ButtonIcon>
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
            <div class="flex justify-center w-full">
              <Dialog.Root>
                <form onsubmit={Game.createStory}>
                  <Dialog.Trigger>
                    <Button class="outline flex flex-row gap-1 p-4 mx-auto m-2">
                      <Icon
                        class="color-gray-800 size-5"
                        src={Plus}
                        theme="solid"
                      />
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
                        <Input id="title" name="title" class="col-span-3" />
                      </div>
                      <div class="grid grid-cols-4 items-center gap-4">
                        <Label for="description" class="text-right"
                          >Description</Label
                        >
                        <Input
                          id="description"
                          name="description"
                          class="col-span-3"
                        />
                      </div>
                      <div class="grid grid-cols-4 items-center gap-4">
                        <Label for="issue-description" class="text-right"
                          >Score</Label
                        >
                        <Select.Root>
                          <Select.Trigger class="w-[100px]">
                            <Select.Value placeholder="Score" />
                          </Select.Trigger>
                          <Select.Content>
                            <Select.Group>
                              <ScrollArea class="h-20">
                                {#each scores as score}
                                  <Select.Item
                                    value={score.value}
                                    label={score.value}
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
            </div>
          </div>
        </Tabs.Content>

        <Tabs.Content value="profile-settings"
          ><div class="grid gap-2 p-5 my-5 border border-gray-300 rounded-lg">
            <div class="flex gap-4 flex-col">
              <div class="flex flex-row justify-between items-center">
                <div>
                  <p class="font-bold text-xl">Profile Settings</p>
                  <p class="text-sm">Make changes to your Profile Settings.</p>
                </div>
              </div>

              <div class="flex gap-4 items-center">
                <Label for="name"
                  ><p class="whitespace-nowrap">Username</p></Label
                >
                <Input
                  id="name"
                  bind:value={Game.storage.user.username}
                  type="text"
                />
              </div>
            </div>
            <div class="flex justify-center w-full">
              <Button
                builders={[]}
                class="outline flex flex-row gap-1 p-4 mx-auto m-2"
              >
                <Icon class="color-gray-800 size-5" src={Check} theme="solid" />
                <div>Save</div>
              </Button>
            </div>
          </div></Tabs.Content
        >
      </Tabs.Root>
    </Sheet.Content>
  </Sheet.Root>
  <div class="absolute left-0 bottom-4 flex justify-center items-center w-full">
    <div
      class="bg-white rounded-xl flex px-4 py-2 gap-4 flex-col border-2 border-gray-300"
    >
      <div class="flex items-center gap-4 justify-center text-gray-700 mt-1">
        <Tooltip.Root>
          <Tooltip.Trigger>
            <ButtonIcon onclick={() => Game.restart()}>
              <Icon
                class="color-gray-700 size-6"
                src={ArrowPath}
                theme="solid"
              />
            </ButtonIcon>
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
            >
              <Icon
                class="color-gray-800 size-6"
                src={ArrowRightEndOnRectangle}
                theme="solid"
              />
            </ButtonIcon>
          </Tooltip.Trigger>
          <Tooltip.Content>
            <p>Exit Room</p>
          </Tooltip.Content>
        </Tooltip.Root>

        <DropdownMenu.Root>
          <DropdownMenu.Trigger>
            <Tooltip.Root>
              <Tooltip.Trigger>
                <ButtonIcon>
                  <Icon
                    class="color-gray-800 size-6"
                    src={Share}
                    theme="outline"
                  />
                </ButtonIcon>
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
              <DropdownMenu.Item onclick={() => copyRoomIdClipboard()}>
                Share Id
                <DropdownMenu.Shortcut
                  ><Icon
                    class="color-gray-800 size-4"
                    src={Square2Stack}
                    theme="outline"
                  /></DropdownMenu.Shortcut
                >
              </DropdownMenu.Item>
              <DropdownMenu.Item>
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
  </div>
{/if}
