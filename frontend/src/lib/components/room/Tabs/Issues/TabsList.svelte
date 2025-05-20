<script lang="ts">
  import { Game } from "$lib/Game.svelte";
  import * as Tabs from "$lib/components/ui/tabs/index.js";
</script>

<Tabs.List class="w-full flex bg-gray-200 mt-5 justify-between gap-1">
  <Tabs.Trigger class="font-semibold bg-white flex-1" value="profile-settings"
    >Profile Settings
  </Tabs.Trigger>
  {#if Game.storage.user.role == "Administrator"}
    <Tabs.Trigger class="font-semibold bg-white flex-1" value="issues"
      >Issues
    </Tabs.Trigger>
  {/if}
</Tabs.List>
