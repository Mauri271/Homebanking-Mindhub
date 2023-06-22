const { createApp } = Vue

const main = createApp({
  data() {
  return {
    showMessage: false,
    message: "Navigate trough the items",
    showNumbers:false,
    num1: "1",
    num2: "2",
    num3: "3",
    num4: "4",
    num5: "5",
      };
  },

  created(){
    
  },

  methods:{

    sectionsAlert(){
        this.showMessage = true;
        
      this.showNumbers = true
    }
  }

}).mount('#main')

const app = createApp({
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
  }

})
  .mount('#app')
  