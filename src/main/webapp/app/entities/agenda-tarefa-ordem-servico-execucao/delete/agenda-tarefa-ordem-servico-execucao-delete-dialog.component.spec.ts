jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AgendaTarefaOrdemServicoExecucaoService } from '../service/agenda-tarefa-ordem-servico-execucao.service';

import { AgendaTarefaOrdemServicoExecucaoDeleteDialogComponent } from './agenda-tarefa-ordem-servico-execucao-delete-dialog.component';

describe('AgendaTarefaOrdemServicoExecucao Management Delete Component', () => {
  let comp: AgendaTarefaOrdemServicoExecucaoDeleteDialogComponent;
  let fixture: ComponentFixture<AgendaTarefaOrdemServicoExecucaoDeleteDialogComponent>;
  let service: AgendaTarefaOrdemServicoExecucaoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AgendaTarefaOrdemServicoExecucaoDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(AgendaTarefaOrdemServicoExecucaoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AgendaTarefaOrdemServicoExecucaoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AgendaTarefaOrdemServicoExecucaoService);
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
