
    adapt(item: any): Consulting {
        return new Consulting(
            item.idConsulting,
            item.speciality,
            item.notes,
            this.teamAdapter.adapt(item.team),
            this.plannedTimingAvailabilityAdapter.adapt(item.plannedTimingAvailability),
            this.plannedTimingConsultingAdapter.adapt(item.plannedTimingConsulting)
        );
    }
}