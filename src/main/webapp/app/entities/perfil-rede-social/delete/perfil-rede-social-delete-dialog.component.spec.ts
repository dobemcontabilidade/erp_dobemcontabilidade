jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { PerfilRedeSocialService } from '../service/perfil-rede-social.service';

import { PerfilRedeSocialDeleteDialogComponent } from './perfil-rede-social-delete-dialog.component';

describe('PerfilRedeSocial Management Delete Component', () => {
  let comp: PerfilRedeSocialDeleteDialogComponent;
  let fixture: ComponentFixture<PerfilRedeSocialDeleteDialogComponent>;
  let service: PerfilRedeSocialService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PerfilRedeSocialDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(PerfilRedeSocialDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PerfilRedeSocialDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PerfilRedeSocialService);
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
