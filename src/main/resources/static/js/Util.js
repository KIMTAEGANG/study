const StringUtils = {
    isEmpty: (value) => {
        if(value === '') {
            return true;
        }
        return false
    },
    isNull: (value) => {
        if(value === null) {
            return true;
        }
        return false;
    },
    isUndefind: (value) => {
        if(value === undefined) {
            return true;
        }
        return false;
    },
    isAllEmpty: (value) => {
        if(this.isEmpty(value) && this.isNull(value) && this.isUndefind(value)) {
            return true;
        }
        return false;
    }
}