package io.github.rafafrdz.api.model

import derevo.circe.magnolia.{decoder, encoder}
import derevo.derive

@derive(encoder, decoder)
case class Feedback(reviews: Seq[Review])
