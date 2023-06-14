const { createApp } = Vue

createApp({
  data() {
  return {
    showModal: true,
      };
  },

  created(){
    
  },

  methods:{

    closeModal(){        
      this.showModal = false
    },

    openModal(){
        this.showModal =true
    }

  }

}).mount('#app')