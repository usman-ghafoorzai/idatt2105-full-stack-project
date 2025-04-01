
<template>
    <div class="searchbar-container">
        <input type="search" id="search" placeholder="Search..."></input>
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
        width: 100%;
        height: 44px;
        border-radius: 10px;
        box-sizing: border-box
    }
    #search-icon {
        position: absolute;
        transform: scaleX(-1);
        transform: translateY(-50%);
        right: 10px;
        top: 50%;
        font-size: 22px;
    }

    @media (max-width: 768px) {
        #search {
            width: 100%;
            height: 40px;
            font-size: 16px;
        }
        #search-icon {
            font-size: 20px;
        }
    }
</style>