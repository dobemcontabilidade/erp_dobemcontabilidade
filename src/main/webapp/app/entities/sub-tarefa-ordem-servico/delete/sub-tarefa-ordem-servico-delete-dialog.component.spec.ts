jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { SubTarefaOrdemServicoService } from '../service/sub-tarefa-ordem-servico.service';

import { SubTarefaOrdemServicoDeleteDialogComponent } from './sub-tarefa-ordem-servico-delete-dialog.component';

describe('SubTarefaOrdemServico Management Delete Component', () => {
  let comp: SubTarefaOrdemServicoDeleteDialogComponent;
  let fixture: ComponentFixture<SubTarefaOrdemServicoDeleteDialogComponent>;
  let service: SubTarefaOrdemServicoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SubTarefaOrdemServicoDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(SubTarefaOrdemServicoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SubTarefaOrdemServicoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SubTarefaOrdemServicoService);
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
