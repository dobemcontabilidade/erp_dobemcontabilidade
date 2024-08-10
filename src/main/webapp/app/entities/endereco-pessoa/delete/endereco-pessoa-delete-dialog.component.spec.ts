jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { EnderecoPessoaService } from '../service/endereco-pessoa.service';

import { EnderecoPessoaDeleteDialogComponent } from './endereco-pessoa-delete-dialog.component';

describe('EnderecoPessoa Management Delete Component', () => {
  let comp: EnderecoPessoaDeleteDialogComponent;
  let fixture: ComponentFixture<EnderecoPessoaDeleteDialogComponent>;
  let service: EnderecoPessoaService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EnderecoPessoaDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(EnderecoPessoaDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EnderecoPessoaDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(EnderecoPessoaService);
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
