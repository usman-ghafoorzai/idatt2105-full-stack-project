
<template>
    <div class="searchbar-container">
        <input type="text" id="search" placeholder="Search..."></input>
        <fa icon="fas fa-magnifying-glass" id="search-icon"></fa>
    </div>
</template>

<style scoped>
    .searchbar-container {
        position: relative;
        display: inline-block;
    }
    #search {
        padding-top: 10px;
        padding-left: 10px;
        font-size: 18px;
        width: 450px;
        height: 44px;
        border-radius: 10px;
    }
    #search-icon {
        position: absolute;
        transform: scaleX(-1);
        right: 10px;
        top: 14px;
        font-size: 22px;
    }
</style>