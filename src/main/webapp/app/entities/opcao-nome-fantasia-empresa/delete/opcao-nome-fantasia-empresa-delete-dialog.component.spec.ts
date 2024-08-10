jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { OpcaoNomeFantasiaEmpresaService } from '../service/opcao-nome-fantasia-empresa.service';

import { OpcaoNomeFantasiaEmpresaDeleteDialogComponent } from './opcao-nome-fantasia-empresa-delete-dialog.component';

describe('OpcaoNomeFantasiaEmpresa Management Delete Component', () => {
  let comp: OpcaoNomeFantasiaEmpresaDeleteDialogComponent;
  let fixture: ComponentFixture<OpcaoNomeFantasiaEmpresaDeleteDialogComponent>;
  let service: OpcaoNomeFantasiaEmpresaService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [OpcaoNomeFantasiaEmpresaDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(OpcaoNomeFantasiaEmpresaDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OpcaoNomeFantasiaEmpresaDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(OpcaoNomeFantasiaEmpresaService);
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
