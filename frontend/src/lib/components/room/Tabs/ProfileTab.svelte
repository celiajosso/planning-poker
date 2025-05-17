<script lang="ts">
  import { Game } from "$lib/Game.svelte";
  import { Icon } from "@steeze-ui/svelte-icon";
  import { Check } from "@steeze-ui/heroicons";

  import * as Tabs from "$lib/components/ui/tabs/index.js";
  import { Button } from "$lib/components/ui/button/index.js";
  import { Label } from "$lib/components/ui/label";
  import { Input } from "$lib/components/ui/input";
</script>

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
        <Label for="name"><p class="whitespace-nowrap">Username</p></Label>
        <Input id="name" bind:value={Game.storage.user.username} type="text" />
      </div>
    </div>
    <div class="flex justify-center w-full">
      <Button
        builders={[]}
        class="outline flex flex-row gap-1 p-4 mx-auto m-2"
        onclick={() => Game.updateUser(Game.storage.user)}
      >
        <Icon class="color-gray-800 size-5" src={Check} theme="solid" />
        <div>Save</div>
      </Button>
    </div>
  </div></Tabs.Content
>
