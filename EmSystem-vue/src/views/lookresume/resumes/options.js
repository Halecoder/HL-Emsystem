import {
  getResume
} from '@/api/stu/resume'
import {
  terms
} from '@/terms'

import parseTime from '@/utils/ruoyi'

// Called by templates to decrease redundancy
function getVueOptions(name) {
  const opt = {
    name: name,
    data() {
      return {
        person: '',
        terms: terms
      }
    },
    computed: {
      lang() {
        const defaultLang = this.terms.cn
        const useLang = this.terms[this.person.lang]

        // overwrite non-set fields with default lang
        Object.keys(defaultLang)
          .filter(k => !useLang[k])
          .forEach(k => {
            console.log(k)
            useLang[k] = defaultLang[k]
          })

        return useLang
      },

      contactLinks() {
        const links = {}

        if (this.person.contact.github) {
          links.github = `https://github.com/${this.person.contact.github}`
        }

        if (this.person.contact.codefights) {
          links.codefights = `https://codefights.com/profile/${this.person.contact.codefights}`
        }

        if (this.person.contact.medium) {
          links.medium = `https://medium.com/@${this.person.contact.medium}`
        }

        if (this.person.contact.email) {
          links.email = `mailto:${this.person.contact.email}`
        }

        if (this.person.contact.linkedin) {
          links.linkedin = `https://linkedin.com/in/${this.person.contact.linkedin}`
        }

        if (this.person.contact.phone) {
          links.phone = `tel:${this.person.contact.phone}`
        }

        return links
      }
    },
    created() {
      this.getList()
    },
    methods: {
      getList() {
        getResume(this.$store.getters.username).then(response => {
          this.loading = false
          if (response.data == null) {
            this.resetForm()
          } else {
            this.person = response.data
            console.log(this.person)
          }
        }).finally(() => {
          this.loading = false
        }).catch(() => {})
      }
    }
  }
  return opt
}

export {
  getVueOptions
}
