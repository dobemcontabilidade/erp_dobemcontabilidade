jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AnexoRequeridoTarefaOrdemServicoService } from '../service/anexo-requerido-tarefa-ordem-servico.service';

import { AnexoRequeridoTarefaOrdemServicoDeleteDialogComponent } from './anexo-requerido-tarefa-ordem-servico-delete-dialog.component';

describe('AnexoRequeridoTarefaOrdemServico Management Delete Component', () => {
  let comp: AnexoRequeridoTarefaOrdemServicoDeleteDialogComponent;
  let fixture: ComponentFixture<AnexoRequeridoTarefaOrdemServicoDeleteDialogComponent>;
  let service: AnexoRequeridoTarefaOrdemServicoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AnexoRequeridoTarefaOrdemServicoDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(AnexoRequeridoTarefaOrdemServicoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AnexoRequeridoTarefaOrdemServicoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AnexoRequeridoTarefaOrdemServicoService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
