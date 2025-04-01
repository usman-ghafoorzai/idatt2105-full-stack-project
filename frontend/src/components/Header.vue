<script setup>

</script>


<template>
    <div id="header">
        <div>
            <h2 id="logo">LOGO</h2>
        </div>
        <div>
            <h3 id="home">HOME</h3>
            <fa icon="home" />
        </div>
        <div>
            <h3 id="categories">CATEGORIES</h3>
        </div>
        <div>
            <h3 id="sell">SELL</h3>
        </div>
        <input type="text" id="search"></input>
    </div>

</template>


<style scoped>
    #header {
        background-color: #E2E5F6;
        height: 133px;
        width: 100%;
        display: grid;
        grid-template-areas: 'logo home categories sell search';
        grid-template-columns: 3fr 1fr 1fr 1fr 3fr;
        align-content: center;
    }

    div {
        display: flex;
        justify-content: center;
        align-items: center;
    }
    #logo {
        grid-area: logo;
        font-size: 48px;
        margin-left: 50px;
        margin-top: 35px;
        font-weight: bold;
    }
    #home {
        grid-area: home;
        font-size: 20px;
        margin-top: 66px;
        margin-bottom: 42px;
    }
    #categories {
        grid-area: categories;
        font-size: 20px;
        margin-top: 66px;
        margin-bottom: 42px;
    }
    #sell {
        grid-area: sell;
        font-size: 20px;
        margin-top: 66px;
        margin-bottom: 42px;
    }
    #search {
        grid-area: search;
        height: 44px;
        font-size: 20px;
        margin-top: 55px;
        margin-right: 29px;
        margin-bottom: 35px;
        border-radius: 10px;
    }
</style>