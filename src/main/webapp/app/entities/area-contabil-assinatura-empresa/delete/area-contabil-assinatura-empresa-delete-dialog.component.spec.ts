jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AreaContabilAssinaturaEmpresaService } from '../service/area-contabil-assinatura-empresa.service';

import { AreaContabilAssinaturaEmpresaDeleteDialogComponent } from './area-contabil-assinatura-empresa-delete-dialog.component';

describe('AreaContabilAssinaturaEmpresa Management Delete Component', () => {
  let comp: AreaContabilAssinaturaEmpresaDeleteDialogComponent;
  let fixture: ComponentFixture<AreaContabilAssinaturaEmpresaDeleteDialogComponent>;
  let service: AreaContabilAssinaturaEmpresaService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AreaContabilAssinaturaEmpresaDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(AreaContabilAssinaturaEmpresaDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AreaContabilAssinaturaEmpresaDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AreaContabilAssinaturaEmpresaService);
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
