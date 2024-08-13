jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { DemissaoFuncionarioService } from '../service/demissao-funcionario.service';

import { DemissaoFuncionarioDeleteDialogComponent } from './demissao-funcionario-delete-dialog.component';

describe('DemissaoFuncionario Management Delete Component', () => {
  let comp: DemissaoFuncionarioDeleteDialogComponent;
  let fixture: ComponentFixture<DemissaoFuncionarioDeleteDialogComponent>;
  let service: DemissaoFuncionarioService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DemissaoFuncionarioDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(DemissaoFuncionarioDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DemissaoFuncionarioDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DemissaoFuncionarioService);
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
