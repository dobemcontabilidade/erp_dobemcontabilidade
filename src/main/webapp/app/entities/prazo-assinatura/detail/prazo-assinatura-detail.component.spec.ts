import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PrazoAssinaturaDetailComponent } from './prazo-assinatura-detail.component';

describe('PrazoAssinatura Management Detail Component', () => {
  let comp: PrazoAssinaturaDetailComponent;
  let fixture: ComponentFixture<PrazoAssinaturaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PrazoAssinaturaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PrazoAssinaturaDetailComponent,
              resolve: { prazoAssinatura: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PrazoAssinaturaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PrazoAssinaturaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load prazoAssinatura on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PrazoAssinaturaDetailComponent);

      // THEN
      expect(instance.prazoAssinatura()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
