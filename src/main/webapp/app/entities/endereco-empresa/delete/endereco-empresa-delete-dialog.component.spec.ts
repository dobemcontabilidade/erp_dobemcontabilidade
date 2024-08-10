jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { EnderecoEmpresaService } from '../service/endereco-empresa.service';

import { EnderecoEmpresaDeleteDialogComponent } from './endereco-empresa-delete-dialog.component';

describe('EnderecoEmpresa Management Delete Component', () => {
  let comp: EnderecoEmpresaDeleteDialogComponent;
  let fixture: ComponentFixture<EnderecoEmpresaDeleteDialogComponent>;
  let service: EnderecoEmpresaService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EnderecoEmpresaDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(EnderecoEmpresaDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EnderecoEmpresaDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(EnderecoEmpresaService);
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
