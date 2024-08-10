import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPrazoAssinatura } from '../prazo-assinatura.model';
import { PrazoAssinaturaService } from '../service/prazo-assinatura.service';
import { PrazoAssinaturaFormService, PrazoAssinaturaFormGroup } from './prazo-assinatura-form.service';

@Component({
  standalone: true,
  selector: 'jhi-prazo-assinatura-update',
  templateUrl: './prazo-assinatura-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PrazoAssinaturaUpdateComponent implements OnInit {
  isSaving = false;
  prazoAssinatura: IPrazoAssinatura | null = null;

  protected prazoAssinaturaService = inject(PrazoAssinaturaService);
  protected prazoAssinaturaFormService = inject(PrazoAssinaturaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PrazoAssinaturaFormGroup = this.prazoAssinaturaFormService.createPrazoAssinaturaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prazoAssinatura }) => {
      this.prazoAssinatura = prazoAssinatura;
      if (prazoAssinatura) {
        this.updateForm(prazoAssinatura);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const prazoAssinatura = this.prazoAssinaturaFormService.getPrazoAssinatura(this.editForm);
    if (prazoAssinatura.id !== null) {
      this.subscribeToSaveResponse(this.prazoAssinaturaService.update(prazoAssinatura));
    } else {
      this.subscribeToSaveResponse(this.prazoAssinaturaService.create(prazoAssinatura));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrazoAssinatura>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(prazoAssinatura: IPrazoAssinatura): void {
    this.prazoAssinatura = prazoAssinatura;
    this.prazoAssinaturaFormService.resetForm(this.editForm, prazoAssinatura);
  }
}
