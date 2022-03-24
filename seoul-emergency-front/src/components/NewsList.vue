<template>
  <div class="news-list">
    <b-tabs>
        <b-tab title="재난 뉴스">
            <ul class="list">
                <li class="news" v-for="index in 4" :key="index">
                    <div class="news-left">
                        <a href="#" target="_blank">
                            <img src="https://via.placeholder.com/120x90?text=News">
                        </a>
                    </div>
                    <div class="news-body">
                        <h4 class="news-heading">
                            <a href="#" target="_blank">
                                Lorem ipsum dolor sit amet, consectetur adip
                            </a>
                        </h4>
                        <h5>0000/00/00 00:00:00</h5>
                        <p>Lorem ipsum dolor sit amet, consectetur adip</p>
                    </div>
                </li>
            </ul>
        </b-tab>
    </b-tabs>
  </div>
</template>

<script>
export default {
    name: 'NewsList'
}
</script>

<style scoped>
.news {
    border-bottom: 1px solid lightgray;
    padding-top: 10px;
    list-style-type: none;
}
</style>