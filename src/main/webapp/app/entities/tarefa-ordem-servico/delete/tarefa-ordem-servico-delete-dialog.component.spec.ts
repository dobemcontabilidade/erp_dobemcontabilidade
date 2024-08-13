jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TarefaOrdemServicoService } from '../service/tarefa-ordem-servico.service';

import { TarefaOrdemServicoDeleteDialogComponent } from './tarefa-ordem-servico-delete-dialog.component';

describe('TarefaOrdemServico Management Delete Component', () => {
  let comp: TarefaOrdemServicoDeleteDialogComponent;
  let fixture: ComponentFixture<TarefaOrdemServicoDeleteDialogComponent>;
  let service: TarefaOrdemServicoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TarefaOrdemServicoDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(TarefaOrdemServicoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TarefaOrdemServicoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TarefaOrdemServicoService);
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
