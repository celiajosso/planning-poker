<script lang="ts">
  import SaveButton from "./Profile/SaveButton.svelte";
  import ProfileInput from "./Profile/ProfileInput.svelte";
</script>

<div class="flex flex-col p-5 my-5 border border-gray-300 rounded-lg">
  <p class="font-bold text-xl">Profile Settings</p>
  <p class="text-sm">Make changes to your Profile Settings.</p>
  <ProfileInput />
  <SaveButton />
</div>
